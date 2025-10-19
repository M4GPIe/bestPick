# bestPick

## Entidades en BD

| Servicio dueño | BD (tipo)               | Esquema / Colección | Descripción                            | Claves/Campos principales                                                                                                                                                 | Índices / TTL                                               | Relaciones/Notas                                |
| -------------- | ----------------------- | ------------------- | -------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------- | ----------------------------------------------- |
| Auth           | `auth_db` (SQL)         | `users`             | Identidad base                         | `id (UUID PK)`, `email (unique)`, `password_hash`, `created_at`, `status`                                                                                                 | `UNIQUE(email)`                                             | Referenciado por casi todo vía `user_id`        |
| Auth           | `auth_db` (SQL)         | `oauth_clients`     | Apps OAuth                             | `client_id PK`, `client_secret`, `redirect_uris`                                                                                                                          |                                                             |                                                 |
| Auth           | `auth_db` (SQL)         | `oauth_tokens`      | Tokens/refresh                         | `token_id PK`, `user_id FK`, `expires_at`                                                                                                                                 | `INDEX(user_id)`                                            |                                                 |
| Perfiles       | `user_db` (SQL)         | `profiles`          | Perfil público                         | `user_id PK/FK`, `handle UNIQUE`, `display_name`, `bio`, `avatar_url`, `region`, `settings(jsonb)`                                                                        | `UNIQUE(handle)`, `INDEX(region)`                           | FK a `auth_db.users.id`                         |
| Social Graph   | `social_db` (SQL)       | `follows`           | Seguimientos                           | `(follower_id, followee_id) PK`, `created_at`                                                                                                                             | `INDEX(follower_id)`, `INDEX(followee_id)`                  | “Amigos” = mutua existencia                     |
| Social Graph   | `social_db` (SQL)       | `blocks`            | Bloqueos                               | `(blocker_id, blocked_id) PK`, `created_at`, `visible_to_friends`(bool)                                                                                                   | `INDEX(blocker_id)`, `INDEX(blocked_id)`                    | Visible a amigos del `blocker`                  |
| Text Posts     | `textposts_db` (Mongo)  | `posts`             | Posts de texto                         | `_id`, `author_id`, `text`, `hashtags[]`, `created_at`, `visibility`, `score_snapshots{monthly, yearly}`                                                                  | `{author_id, created_at}`, `text` (FTS), `hashtags`         | Metadatos; votos en servicio aparte             |
| Text Posts     | `textposts_db` (Mongo)  | `comments`          | Comentarios tipo YouTube (no anidados) | `_id`, `post_id`, `author_id`, `text`, `mentions[]` (handles), `created_at`, `conversation_id`                                                                            | `post_id`, `conversation_id`, FTS                           | `conversation_id` calculado por Comment Service |
| Votes          | `votes_db` (SQL)        | `post_votes`        | Votos -3..+3 (cubre texto e imagen)    | `(user_id, post_id) PK`, `post_type`(`text`,`media`), `value`(-3..+3), `created_at`, `updated_at`                                                                         | `INDEX(post_id)`, `INDEX(user_id)`                          | Publica eventos de cambios                      |
| Media          | `mediaposts_db` (Mongo) | `media_posts`       | Posts de imagen/vídeo                  | `_id`, `author_id`, `caption`, `hashtags[]`, `assets[{type,image/video,url,duration,thumb}]`, `created_at`, `processing_status`                                           | `author_id`, `created_at`, `hashtags`                       | Bytes en objeto; sin comentarios                |
| Feed           | `feed_db` (Mongo)       | `timelines_text`    | Cache de feed texto por usuario        | `_id: user_id`, `items[{post_id, reason(friend/follow/reco), rank}]`, `updated_at`                                                                                        | `TTL opcional`                                              | Construido por Feed Service                     |
| Feed           | `feed_db` (Mongo)       | `timelines_media`   | Cache de feed media por usuario        | Igual                                                                                                                                                                     | Igual                                                       | Igual                                           |
| Recos          | `reco_db` (Mongo)       | `user_preferences`  | Preferencias por hashtags/temas        | `_id: user_id`, `hashtag_score{tag:float}`, `topic_score{topic:float}`, `last_update`                                                                                     | `INDEX(last_update)`                                        | Actualizado por eventos de votos                |
| Recos          | `reco_db` (Mongo)       | `hashtag_stats`     | Stats por hashtag                      | `_id: hashtag`, `use_count`, `ctr`, `score`                                                                                                                               |                                                             |                                                 |
| Rankings       | `ranking_db` (SQL)      | `leaderboards`      | Líderboards agregados                  | `id PK`, `scope`(`regional`,`global`), `region NULLABLE`, `content_type`(`text`,`media`), `period`(`monthly`,`yearly`), `period_key`(YYYY-MM), `user_id`, `score`, `rank` | `INDEX(period,scope,content_type,region)`, `INDEX(user_id)` | Consumido por API Rankings                      |
| Stories        | `stories_db` (Mongo)    | `stories`           | Stories 24h                            | `_id`, `author_id`, `assets[]`, `created_at`, `expires_at`                                                                                                                | `TTL(expires_at)`                                           | Sin votos                                       |
| Stories        | `stories_db` (Mongo)    | `story_views`       | Quién vio qué                          | `_id`, `story_id`, `viewer_id`, `viewed_at`                                                                                                                               | `story_id`, `viewer_id`                                     |                                                 |
| Chat           | `chat_db` (Mongo)       | `conversations`     | Conversaciones                         | `_id`, `user_a`, `user_b`, `reciprocated_at`, `blocked_by`(nullable), `created_at`                                                                                        | `INDEX(user_a)`, `INDEX(user_b)`                            | Anonimato hasta reciprocidad                    |
| Chat           | `chat_db` (Mongo)       | `messages`          | Mensajes                               | `_id`, `conversation_id`, `sender_id`, `text`, `attachments[]`, `created_at`, `delivered`, `read_at`                                                                      | `conversation_id`, `created_at`                             | Mostrar emisor anónimo si !reciprocated         |
| Notificaciones | `notify_db` (Mongo)     | `notifications`     | Inbox de notifs                        | `_id`, `user_id`, `type`, `payload`, `created_at`, `read_at`                                                                                                              | `user_id`, `created_at`                                     | Origen: eventos Kafka                           |
| Búsqueda/Tags  | `search_db` (Mongo)     | `hashtags`          | Hashtags normalizados                  | `_id: tag`, `created_at`, `last_seen_at`                                                                                                                                  |                                                             | También en stats                                |
| Búsqueda/Tags  | `search_db` (Mongo)     | `post_hashtags`     | Invertido post↔hashtag                 | `_id`, `post_id`, `post_type`, `tag`                                                                                                                                      | `tag`, `post_id`                                            |                                                 |

```mermaid
flowchart TB
  subgraph SQL
    AUTH_DB[(auth_db)]
    SOCIAL_DB[(social_db)]
    VOTES_DB[(votes_db)]
    RANK_DB[(ranking_db)]
    USER_DB[(user_db)]
  end

  subgraph MONGO
    TP_DB[(textposts_db)]
    MP_DB[(mediaposts_db)]
    FEED_DB[(feed_db)]
    RECO_DB[(reco_db)]
    STORY_DB[(stories_db)]
    CHAT_DB[(chat_db)]
    SEARCH_DB[(search_db)]
    NOTIF_DB[(notify_db)]
  end

  AUTH_DB --- USER_DB
  USER_DB -->|user_id| SOCIAL_DB
  AUTH_DB -->|user_id| VOTES_DB
  USER_DB -->|user_id/region| RANK_DB

  TP_DB -->|post_id| VOTES_DB
  MP_DB -->|post_id| VOTES_DB

  FEED_DB -->|user_id| USER_DB
  RECO_DB -->|user_id| USER_DB
  STORY_DB -->|author_id| USER_DB
  CHAT_DB -->|user_a/user_b| USER_DB
  NOTIF_DB -->|user_id| USER_DB

  SEARCH_DB -->|tag↔post_id| TP_DB
  SEARCH_DB -->|tag↔post_id| MP_DB

  TP_DB:::mongo --> TP_POSTS[textposts_db.posts]
  TP_DB:::mongo --> TP_COMMENTS[textposts_db.comments]
  MP_DB:::mongo --> MP_POSTS[mediaposts_db.media_posts]
  FEED_DB:::mongo --> TL_TEXT[feed_db.timelines_text]
  FEED_DB:::mongo --> TL_MEDIA[feed_db.timelines_media]
  RECO_DB:::mongo --> PREFS[reco_db.user_preferences]
  STORY_DB:::mongo --> STORIES[stories_db.stories]
  CHAT_DB:::mongo --> CONVS[chat_db.conversations]
  CHAT_DB:::mongo --> MSGS[chat_db.messages]
  SEARCH_DB:::mongo --> HTS[search_db.hashtags]
  SEARCH_DB:::mongo --> PHTS[search_db.post_hashtags]
  NOTIF_DB:::mongo --> NOTIFS[notify_db.notifications]

  classDef mongo fill:#eef,stroke:#99f;
```

## Microservicios

| Servicio       | Responsabilidades clave        | BD propia         | Endpoints (ejemplos)                                                                 | Publica (Kafka)                                                          | Consume (Kafka)                                                              | Escalado/Notas                                           |
| -------------- | ------------------------------ | ----------------- | ------------------------------------------------------------------------------------ | ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- | -------------------------------------------------------- |
| API Gateway    | Enrutado, rate-limit, authn    | —                 | `/api/**`                                                                            | —                                                                        | —                                                                            | Spring Cloud Gateway                                     |
| Auth (OAuth)   | OAuth2/OIDC, tokens            | `auth_db` (SQL)   | `/oauth/token`, `/oauth/authorize`, `/users`                                         | `user.created`, `user.updated`                                           | —                                                                            | Spring Authorization Server                              |
| User Profile   | Perfil y settings              | `user_db` (SQL)   | `GET/PUT /profiles/{id}`                                                             | `user.region.updated`                                                    | `user.created`                                                               | Cache regional para rankings                             |
| Social Graph   | Follows, amigos, bloqueos      | `social_db` (SQL) | `POST /follow`, `POST /block`, `GET /friends`                                        | `follow.created/deleted`, `block.created/deleted`, `friendship.updated`  | `user.created`                                                               | Consulta mutua para “amigos”                             |
| Text Post      | Crear/leer posts texto         | `textposts_db`    | `POST /text-posts`, `GET /text-posts/{id}`                                           | `post.text.created`, `post.deleted`, `hashtags.extracted`                | `vote.*`, `comment.*`                                                        | Extrae hashtags al crear                                 |
| Comment        | Comentarios de texto           | `textposts_db`    | `POST /text-posts/{id}/comments`                                                     | `comment.created/deleted`, `conversation.updated`                        | `post.text.created`                                                          | Calcula `conversation_id` por menciones y grafos ligeros |
| Media Post     | Imagen/Vídeo (sin comentarios) | `mediaposts_db`   | `POST /media-posts`, `GET /media-posts/{id}`                                         | `post.media.created`, `post.deleted`, `hashtags.extracted`               | `vote.*`                                                                     | Firma URLs de subida/descarga                            |
| Vote           | Votos [-3..+3]                 | `votes_db` (SQL)  | `PUT /votes/{postId}`                                                                | `vote.added/updated/removed`                                             | `post.*.created`, `post.deleted`                                             | Idempotencia por `(user,post)`                           |
| Feed           | Construcción de timelines      | `feed_db`         | `GET /feeds/text`, `GET /feeds/media`, `POST /feeds/rebuild`                         | `feed.rebuild.completed`                                                 | `post.*.created`, `follow.*`, `friendship.updated`, `recommendation.updated` | Interleaving: amigos→seguidos→reco                       |
| Recomendación  | Preferencias/temas/hashtags    | `reco_db`         | `GET /reco/suggestions`                                                              | `recommendation.updated`                                                 | `vote.*`, `hashtags.extracted`, `post.*.created`                             | Actualiza scores usuario-hashtag                         |
| Ranking        | Leaderboards mensual/anual     | `ranking_db`      | `/rankings?scope=regional\|global&period=monthly\|yearly&content=text\|media`       | `ranking.updated`                                                        | `vote.*`, `user.region.updated`                                              | Kafka Streams ventanas por período                       |
| Stories        | Stories 24h                    | `stories_db`      | `POST /stories`, `GET /stories/following`                                            | `story.created`, `story.expired`                                         | `follow.*`                                                                   | TTL índice por `expires_at`                              |
| Chat           | Mensajería con anonimato       | `chat_db`         | `POST /conversations/{user}`, `POST /messages`                                       | `chat.message.created`, `chat.conversation.reciprocated`, `chat.blocked` | `block.*`                                                                    | Oculta perfil hasta reciprocidad                         |
| Notificaciones | Fanout de eventos a inbox      | `notify_db`       | `GET /notifications`                                                                 | —                                                                        | (casi todos)                                                                 | Tipos: follow, comment, vote, msg                        |
| Search/Tags    | Descubrir por hashtag          | `search_db`       | `GET /search?tag=`                                                                   | `tag.stats.updated`                                                      | `hashtags.extracted`                                                         | Texto y hashtag inverted index                           |

```mermaid
flowchart LR
  subgraph Clients
    A[Mobile/Web]
  end
  A --> GW[API Gateway]

  subgraph Core
    AUTH[Auth/OAuth]
    PROF[User Profile]
    SG[Social Graph]
    TP[Text Post]
    CM[Comment]
    MP[Media Post]
    VT[Vote]
    FD[Feed]
    RC[Recomendación]
    RK[Ranking]
    ST[Stories]
    CH[Chat]
    NF[Notificaciones]
    SR[Search/Tags]
  end

  GW --> AUTH
  GW --> PROF
  GW --> SG
  GW --> TP
  GW --> CM
  GW --> MP
  GW --> VT
  GW --> FD
  GW --> RC
  GW --> RK
  GW --> ST
  GW --> CH
  GW --> NF
  GW --> SR

  subgraph Kafka[Kafka Topics]
    U1[user.created]
    U2[user.region.updated]
    F1[follow.created]
    F2[follow.deleted]
    FR[friendship.updated]
    B1[block.created]
    B2[block.deleted]
    PT[post.text.created]
    PM[post.media.created]
    PD[post.deleted]
    HX[hashtags.extracted]
    C1[comment.created]
    C2[comment.deleted]
    CU[conversation.updated]
    V1[vote.added]
    V2[vote.updated]
    V3[vote.removed]
    R1[recommendation.updated]
    L1[ranking.updated]
    S1[story.created]
    S2[story.expired]
    M1[chat.message.created]
    CR[chat.conversation.reciprocated]
    FB[feed.rebuild.completed]
  end

  AUTH --> U1
  PROF --> U2
  SG --> F1
  SG --> F2
  SG --> FR
  SG --> B1
  SG --> B2
  TP --> PT
  TP --> HX
  MP --> PM
  MP --> HX
  CM --> C1
  CM --> C2
  CM --> CU
  VT --> V1
  VT --> V2
  VT --> V3
  RC --> R1
  RK --> L1
  ST --> S1
  ST --> S2
  CH --> M1
  CH --> CR
  FD --> FB

  U1 --> PROF
  U2 --> PROF
  F1 --> FD
  F2 --> FD
  FR --> FD
  PT --> FD
  PM --> FD
  PD --> FD
  V1 --> RC
  V1 --> RK
  V1 --> FD
  V2 --> RC
  V2 --> RK
  V2 --> FD
  V3 --> RC
  V3 --> RK
  V3 --> FD
  HX --> RC
  C1 --> FD
  C2 --> FD
  CU --> FD
  S1 --> NF
  S2 --> NF
  F1 --> ST
  FR --> ST
  M1 --> NF
  CR --> NF
  B1 --> CH
  B2 --> CH
  PT --> SR
  PM --> SR
```

# Spring Config Server Demo

This file demonstrate spring config server both client and application part.

## Environment repository structure

This config server searches config repostiory as following patterns

-   {application}
-   common/\*\*

So it can load configs as following patterns from config git repostiory

```
├── order-ms
│  ├── application-dev.yml
│  ├── application-prod.yml
│  ├── application-rony.yml
│  └── application.yml
└── upload-limit
   ├── application-dev.yml
   ├── application-prod.yml
   └── application.yml
```

Check [spring-config-server-demo](https://github.com/nurrony/spring-config-server-demo-configs) for details

## Testing The configs

Use the following command to see it in action

```
curl http://localhost:8888/order-ms/dev # loads order-ms dev profile
curl http://localhost:8888/order-ms/prod # loads order-ms prod profile
curl http://localhost:8888/order-ms,upload-limit/prod # loads order-ms and upload-limit prod profile
```

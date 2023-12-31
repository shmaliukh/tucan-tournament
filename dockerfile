ARG JDK_VERSION
FROM openjdk:${JDK_VERSION}

WORKDIR /app

ARG APP_MODULE_NAME
ENV APP_MODULE_NAME=$APP_MODULE_NAME

ARG APP_VERSION
ENV APP_VERSION=$APP_VERSION

COPY /target/$APP_MODULE_NAME-$APP_VERSION.jar /app

ARG APP_PORT
ENV APP_PORT=$APP_PORT
EXPOSE APP_PORT

CMD java -jar /app/$APP_MODULE_NAME-$APP_VERSION.jar
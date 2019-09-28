# STAGE 0
FROM node:12.2.0-alpine AS dependencies
LABEL stage=dependencies
RUN mkdir /app
WORKDIR /app
COPY ./package*.json /app/
RUN npm install

# STAGE 1
FROM dependencies AS build
LABEL stage=build
COPY ./ /app/
RUN npm run build

# STAGE 2
FROM nginx:alpine AS release
LABEL stage=release
COPY --from=build /app/build/ /usr/share/nginx/html/
EXPOSE 80
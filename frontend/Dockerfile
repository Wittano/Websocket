FROM node:12.21.0

WORKDIR /app

COPY . .

RUN yarn install
ENTRYPOINT [ "yarn", "start" ]
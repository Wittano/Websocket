FROM node:13.14.0-alpine

RUN mkdir -p /usr/src/angular

WORKDIR /usr/src/angular

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json .
COPY package-lock.json .

RUN npm install || (npm cache clean --force && npm install)
RUN npm install -g @angular/cli@9.1.4

COPY . .

EXPOSE 4200

CMD ng serve --host 0.0.0.0 --port 4200

FROM node:18-alpine
WORKDIR /app

RUN apk add --no-cache git

# Клонируем React RealWorld 
RUN git clone https://github.com/gothinkster/react-redux-realworld-example-app.git .

# заменяем node-sass на sass
RUN sed -i 's/"node-sass":.*/"sass": "^1.69.0",/' package.json

RUN npm install

ARG REACT_APP_API_URL=/api
ENV REACT_APP_API_URL=$REACT_APP_API_URL

# Скрипт сборки через command в docker-compose.yml
CMD ["npm", "run", "build"]

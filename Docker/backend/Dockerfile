FROM node:18-slim
WORKDIR /app

# Устанавливаем git и wget
RUN apt-get update && apt-get install -y git wget && rm -rf /var/lib/apt/lists/*

# Клонируем репозиторий
RUN git clone https://github.com/gothinkster/node-express-realworld-example-app.git .

# Устанавливаем зависимости
RUN npm install

# Генерируем Prisma-клиент
RUN npx prisma generate

EXPOSE 3000

# Сначала создаем таблицы в БД, затем запускаем сервер
CMD ["sh", "-c", "npx prisma db push --accept-data-loss && npm start"]

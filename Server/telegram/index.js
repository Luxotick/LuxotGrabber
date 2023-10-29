const express = require('express');
const bodyParser = require('body-parser');
const { Telegraf } = require('telegraf');
const app = express();

app.use(bodyParser.json());

const bot = new Telegraf('bottoken');

app.post('/send-message', (req, res) => {
  const { customMessage } = req.body;

  const chatId = "YourChatIDhere";

  console.log(req.body);

  bot.telegram.sendMessage(chatId, "Program " + customMessage + " tarafından çalıştırıldı. Daha fazla bilgi için discord'a veya sunucuya bakın.")
    .then(() => {
      res.send('Mesaj gönderildi.');
    })
    .catch((error) => {
      console.error('Mesaj gönderme hatası:', error);
      res.status(500).send('Mesaj gönderme hatası.');
    });
});

bot.launch();

const PORT = process.env.PORT || 1453;
app.listen(PORT, () => {
  console.log(`Express uygulaması ${PORT} portunda çalışıyor.`);
});

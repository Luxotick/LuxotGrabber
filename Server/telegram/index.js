const express = require('express')
const bodyParser = require('body-parser')
const Telegraf = require('node-telegram-bot-api');
const app = express();
const fs = require('fs');


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }))

const multer = require('multer');
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/');
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname)
  },
});

const upload = multer({ storage: storage });
app.use(bodyParser.json());

const bot = new Telegraf('token', { polling: true });

app.post('/send-message', (req, res) => {
  const { customMessage } = req.body

  const chatId = "4077368131"

  console.log(req.body);

  bot.sendMessage(chatId, "Program " + customMessage + " tarafından çalıştırıldı. Zip dosyası gönderiliyor...")
    .then(() => {
      res.send('Mesaj gönderildi.')
    })
    .catch((error) => {
      console.error('Mesaj gönderme hatası:', error)
      res.status(500).send('Mesaj gönderme hatası.')
    });
});



app.post('/dosya-yukle', upload.single('dosya'), (req, res) => {
  const uploadedFile = req.file;
  if (!uploadedFile) {
    return res.status(400).json({ message: 'Dosya yüklenemedi.' })
  }

  console.log(uploadedFile)
  let last = uploadedFile.destination + uploadedFile.filename
  sendTelegram(last)


  return res.status(200).json({ message: 'Dosya başarıyla yüklendi.', dosyaAdi: uploadedFile.originalname })


});

function sendTelegram(file){

  const chatId = "chatid"

  bot.sendDocument(chatId, fs.createReadStream(file))
}


const PORT = process.env.PORT || 1453;
app.listen(PORT, () => {
  console.log(`Express uygulaması ${PORT} portunda çalışıyor.`)
});

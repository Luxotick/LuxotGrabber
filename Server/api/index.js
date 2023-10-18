const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');

const app = express();
const port = 3000;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/');
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  },
});

const upload = multer({ storage: storage });

// POST isteği için endpoint
app.post('/dosya-yukle', upload.single('dosya'), (req, res) => {
  const uploadedFile = req.file;
  if (!uploadedFile) {
    return res.status(400).json({ message: 'Dosya yüklenemedi.' });
  }

  return res.status(200).json({ message: 'Dosya başarıyla yüklendi.', dosyaAdi: uploadedFile.originalname });
});

app.listen(port, () => {
  console.log(`Server started on http://localhost:${port}`);
});

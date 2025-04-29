const express = require('express');
const { Op } = require('sequelize');
const { sequelize } = require('./db');
const { Users } = require('./db');
const app = express();

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.post('/register', async (req, res) => {
  const { name, username, password } = req.body;

  if (!name || !username || !password) return res.status(400).json({ error: 'Semua field harus diisi' });

  try {
    const existingUser = await Users.findOne({ where: { username } });
    if (existingUser) {
      return res.status(400).json({ error: 'Username telah digunakan' });
    }

    const newUser = await Users.create({ name, username, password });
    return res.status(201).json({ message: 'User berhasil diregistrasi', data: newUser });
  } catch (error) {
    return res.status(500).json({ error: 'An error occurred during registration.' });
  }
});

app.post('/login', async (req, res) => {
  const { username, password } = req.body;
  if (!username || !password) return res.status(400).json({ error: 'Username dan Password harus diisi' });
  
  try {
    const user = await Users.findOne({ where: { username, password } });
    if (!user) {
      return res.status(400).json({ error: 'Username atau Password salah' });
    }
    return res.status(200).json({ message: 'Login berhasil', data: user });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during login.' });
  }
});




// Initialize the database and sync models
const port = 3000;
sequelize.authenticate()
  .then(() => {
    console.log('database connected successfully');
    app.listen(port, function () {
      console.log(`listening on port ${port}...`);
    });
  })
  .catch((err) => {
    console.error('unable to connect to the database:', err);
  });
const express = require('express');
const { Op } = require('sequelize');
const { sequelize, Group, Member, Chat } = require('./db');
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

// CHATTING API
app.get('/users/:username', async (req, res) => {
  const { username } = req.params;
  if (!username) return res.status(400).json({ error: 'Username harus diisi' });

  try {
    const user = await Users.findOne({ where: { username } });
    if (!user) {
      return res.status(404).json({ error: 'User tidak ditemukan' });
    }
    return res.status(200).json({ message: 'User ditemukan', data: user });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get user.' });
  }
});
app.post('/group', async (req, res) => {
  const { name, members } = req.body;
  if (!members) return res.status(400).json({ error: 'Nama group dan members harus diisi' });

  try {
    const users = await Users.findAll({ where: { username: { [Op.in]: members } } });
    if (users.length !== members.length) {
      return res.status(400).json({ error: 'Ada username yang tidak ditemukan' });
    }

    // check for private duplicate
    if (name === "") {
      const allPrivateChat = await Group.findAll({ where: { name } });
      allPrivateChat.forEach(async (group) => {
        let findMembers = await Member.findAll({ where: { group_id: group.id } });
        let membersUsername = findMembers.map((member) => member.user_username).sort();
        let sortedInputMembers = members.sort();

        if (membersUsername.join(',') == sortedInputMembers.join(',')) {
          return res.status(400).json({ error: 'Sudah pernah anda add sebelumnya' });
        }
      })
    }

    const group = await Group.create({ name });
    const memberPromises = members.map((member) => {
      return Member.create({ group_id: group.id, user_username: member });
    });
    await Promise.all(memberPromises);

    const newMembers = await Member.findAll({ where: { group_id: group.id } });
    return res.status(201).json({ message: 'Group berhasil dibuat', data: { group, users, members: newMembers } });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during create group.' });
  }
});
app.get('/users/:username/groups', async (req, res) => {
  const { username } = req.params;
  if (!username) return res.status(400).json({ error: 'Username harus diisi' });

  try {
    const user = await Users.findOne({ where: { username } });
    if (!user) {
      return res.status(404).json({ error: 'User tidak ditemukan' });
    }

    const groupIds = await Member.findAll({ where: { user_username: username } });
    const groups = await Group.findAll({ where: { id: { [Op.in]: groupIds.map((member) => member.group_id) } } });

    const data = await Promise.all(groups.map(async (group) => {
      const chats = await Chat.findAll({
        where: { group_id: group.id },
        order: [['chat_time', 'DESC']],
      });
      const members = await Member.findAll({ where: { group_id: group.id } });
      const userIds = members.map((member) => member.user_username);
      const users = await Users.findAll({ where: { username: { [Op.in]: userIds } } });
      return {
        group,
        chats,
        members,
        users,
      };
    }));

    return res.status(200).json({ message: 'Group berhasil diambil', data });

  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get groups.' });
  }
});
app.get('/group/:group_id/chat', async (req, res) => {
  const { group_id } = req.params;
  if (!group_id) return res.status(400).json({ error: 'Group ID harus diisi' });

  try {
    const group = await Group.findByPk(group_id);
    if (!group) {
      return res.status(404).json({ error: 'Group tidak ditemukan' });
    }

    const chats = await Chat.findAll({ where: { group_id: group_id } });
    return res.status(200).json({ message: 'Chat berhasil diambil', data: chats });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get chat.' });
  }
});
app.post('/group/:group_id/chat', async (req, res) => {
  const { group_id } = req.params;
  const { username, chat, chat_time } = req.body;
  if (!chat) return res.status(400).json({ error: 'Chat harus diisi' });

  try {
    const group = await Group.findByPk(group_id);
    const user = await Users.findOne({ where: { username } });
    if (!group || !user) {
      return res.status(404).json({ error: 'Group atau user tidak ditemukan' });
    }

    const newChat = await Chat.create({
      group_id: groupId,
      user_username: username,
      user_name: user.name,
      chat, chat_time: chat_time
    });
    return res.status(201).json({ message: 'Chat berhasil dikirim', data: newChat });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during send chat.' });
  }
});

// minor
app.get('/groups', async (req, res) => {
  try {
    const groups = await Group.findAll();
    return res.status(200).json({ message: 'Groups berhasil diambil', data: groups });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get groups.' });
  }
});
app.get('/members', async (req, res) => {
  try {
    const members = await Member.findAll();
    return res.status(200).json({ message: 'Members berhasil diambil', data: members });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get groups.' });
  }
});
app.get('/chats', async (req, res) => {
  try {
    const chats = await Chat.findAll();
    return res.status(200).json({ message: 'Chats berhasil diambil', data: chats });
  } catch (error) {
    console.log(error)
    return res.status(500).json({ error: 'An error occurred during get groups.' });
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
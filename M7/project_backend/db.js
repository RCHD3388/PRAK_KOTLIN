const { Sequelize, Model, DataTypes } = require("sequelize");

const DB_NAME = "t7_222117056";
const DB_USER = "root";
const DB_PASS = "";
const DB_HOST = "localhost";

const sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASS, {
    host: DB_HOST,
    dialect: "mysql"
});

class Users extends Model { }
Users.init({
  username: {
    type: DataTypes.STRING,
    allowNull: false,
    primaryKey: true
  },
  name: {
    type: DataTypes.STRING,
    allowNull: false
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, { sequelize,  underscored: true, timestamps: false, tableName: 'users' });

class Group extends Model {}
Group.init({
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  name: {
    type: DataTypes.STRING(255),
    allowNull: false
  }
}, { sequelize, underscored: true, timestamps: false, tableName: 'groups' });

class Member extends Model {}
Member.init({
  member_id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  group_id: {
    type: DataTypes.INTEGER,
    allowNull: false
  },
  user_username: {
    type: DataTypes.STRING(255),
    allowNull: false
  }
}, { sequelize, underscored: true, timestamps: false, tableName: 'members' });

class Chat extends Model {}
Chat.init({
  chat_id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  group_id: {
    type: DataTypes.INTEGER,
    allowNull: false
  },
  user_username: {
    type: DataTypes.STRING(255),
    allowNull: false
  },
  user_name: {
    type: DataTypes.STRING(255),
    allowNull: false
  },
  chat: {
    type: DataTypes.STRING(255),
    allowNull: false
  },
  chat_time: {
    type: DataTypes.BIGINT,
    allowNull: false
  }
}, { sequelize, underscored: true, timestamps: false, tableName: 'chats' });


module.exports = {
    sequelize, DB_NAME, DB_HOST, DB_PASS, DB_USER, 
    Users, Group, Member, Chat
}
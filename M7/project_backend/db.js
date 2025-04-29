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

module.exports = {
    sequelize, Users, DB_NAME, DB_HOST, DB_PASS, DB_USER
}
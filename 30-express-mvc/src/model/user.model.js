module.exports = (sequelize, Sequelize) => {
    const User = sequelize.define(
        'User',
        {
            id: {
                type: Sequelize.INTEGER,
                primaryKey: true,
                autoIncrement: true,
                field: 'id'
            },
            userId: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'user_id'
            },
            username: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'username'
            },
            password: {
                type: Sequelize.STRING,
                allowNull: true,
                field: 'password'
            },
            phoneNo: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'phone_no'
            },
            email: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'email'
            },
            createdAt: {
                type: Sequelize.DATE,
                field: 'created_at',
                defaultValue: Sequelize.NOW
            },
            updatedAt: {
                type: Sequelize.DATE,
                field: 'updated_at'
            }
        },
        {
            tableName: 't_user',
            indexes: [
                {
                    unique: true,
                    name: 'uk_user_id',
                    fields: ['user_id']
                },
                {
                    unique: true,
                    name: 'uk_username',
                    fields: ['username']
                }
            ],
        }
    );
    // 关联
    User.associate = function (models) { }
    return User;
}


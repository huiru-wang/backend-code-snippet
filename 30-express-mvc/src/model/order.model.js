module.exports = (sequelize, Sequelize) => {
    const Order = sequelize.define(
        'Order',
        {
            id: {
                type: Sequelize.INTEGER,
                primaryKey: true,
                autoIncrement: true,
                field: 'id'
            },
            orderId: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'order_id'
            },
            userId: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'user_id'
            },
            type: {
                type: Sequelize.INTEGER,
                allowNull: false,
                field: 'type'
            },
            status: {
                type: Sequelize.INTEGER,
                allowNull: false,
                field: 'status'
            },
            paymentType: {
                type: Sequelize.INTEGER,
                allowNull: false,
                field: 'payment_type'
            },
            amount: {
                type: Sequelize.DECIMAL(10, 2),
                allowNull: false,
                field: 'amount'
            },
            createdAt: {
                type: Sequelize.DATE,
                field: 'created_at'
            },
            updatedAt: {
                type: Sequelize.DATE,
                field: 'updated_at'
            }
        },
        {
            tableName: 't_order',
            indexes: [
                {
                    unique: true,
                    name: 'uk_order_id',
                    fields: ['order_id']
                },
                {
                    name: 'idx_user_id',
                    fields: ['user_id']
                }
            ],
        }
    );
    // 关联
    Order.associate = function (models) { }
    return Order;
}
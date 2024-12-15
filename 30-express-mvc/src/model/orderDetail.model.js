module.exports = (sequelize, Sequelize) => {
    const OrderDetail = sequelize.define(
        'OrderDetail',
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
            skuId: {
                type: Sequelize.STRING,
                allowNull: false,
                field: 'sku_id'
            },
            price: {
                type: Sequelize.DECIMAL(10, 2),
                allowNull: false,
                field: 'price'
            },
            actualPrice: {
                type: Sequelize.DECIMAL(10, 2),
                allowNull: false,
                field: 'actual_price'
            },
            quantity: {
                type: Sequelize.INTEGER,
                allowNull: false,
                field: 'quantity'
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
            tableName: 't_order_detail',
            indexes: [
                {
                    name: 'idx_orderId',
                    fields: ['order_id']
                }
            ],
        }
    );
    // 关联
    OrderDetail.associate = function (models) { }
    return OrderDetail;
}
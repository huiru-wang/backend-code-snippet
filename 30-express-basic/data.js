const userData = [
    {
        id: 1,
        name: 'John Doe',
        email: 'john@example.com'
    },
    {
        id: 2,
        name: 'Jane Doe',
        email: 'jane@example.com'
    }
];

class UserDataManager {
    constructor(data) {
        this.data = data;
    }

    // 获取所有用户数据
    getAllUsers() {
        return this.data;
    }

    // 根据ID获取用户数据
    getUserById(id) {
        const user = this.data.find(user => user.id === id);
        if (!user) {
            return null;
        }
        return user;
    }

    // 添加用户数据
    addUser(user) {
        if (!user || !user.id || !user.name || !user.email) {
            throw new Error('Invalid user data');
        }
        this.data.push(user);
    }

    // 删除用户数据
    removeUserById(id) {
        this.data = this.data.filter(user => user.id !== id)
        console.log(this.data)
    }
}

// 创建UserDataManager实例
const userManager = new UserDataManager(userData);

// 导出UserDataManager实例
module.exports = userManager;
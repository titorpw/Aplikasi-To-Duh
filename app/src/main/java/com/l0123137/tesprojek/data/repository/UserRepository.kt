package com.l0123137.tesprojek.data.repository

import com.l0123137.tesprojek.data.dao.UserDao
import com.l0123137.tesprojek.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao){
    /**
     * Menyisipkan pengguna baru ke dalam database.
     * Ini adalah suspend function karena memanggil metode suspend dari DAO.
     * @param user Objek User yang akan disimpan.
     */
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    /**
     * Memperbarui data pengguna yang ada di database.
     * @param user Objek User dengan data yang sudah diperbarui.
     */
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    /**
     * Menghapus pengguna dari database.
     * @param user Objek User yang akan dihapus.
     */
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    /**
     * Mengambil data pengguna berdasarkan username.
     * Mengembalikan Flow yang akan emit null jika pengguna tidak ditemukan.
     * @param username Username dari pengguna yang dicari.
     * @return Flow yang berisi objek User atau null.
     */
    fun getUserByUsername(username: String): Flow<User?> {
        return userDao.getUserByUsername(username)
    }

    /**
     * FUNGSI BARU: Mengambil data pengguna berdasarkan ID.
     * Diperlukan untuk fitur pengingat ulang tahun.
     * @param userId ID dari pengguna yang dicari.
     * @return Flow yang berisi objek User atau null.
     */
    fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId)
    }
}
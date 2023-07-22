package com.proxyseller.twitter.service

import com.proxyseller.twitter.entitiy.User
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    def createUser(String username, String email, String password, List<String> following) {//!!!!! List<String>-> List<User>
        User newUser = new User(username, email, password, following as List<User>)
        return userRepository.save(newUser)
    }

    def getUserById(String userId) {
        return userRepository.findById(userId).orElse(null)
    }

    def updateUser(String userId, String username, String email, String password) {
        User userToUpdate = getUserById(userId)
        if (userToUpdate) {
            userToUpdate.username = username
            userToUpdate.email = email
            userToUpdate.password = password
            return userRepository.save(userToUpdate)
        }
        return null
    }

    void deleteUser(String userId) {
        userRepository.deleteById(userId)
    }
}


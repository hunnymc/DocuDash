import { defineStore } from 'pinia'

export const useLoginUserStore = defineStore('loginUser', {
    state: () => ({
        userEmail: null,
        accessToken: null,
        refreshToken: null,
        role: null
    }),

    persist: true,
    
    getters: {
        getUser() {
            return this.user
        },
        getAccessToken() {
            return this.accessToken
        },
        getRefreshToken() {
            return this.refreshToken
        },
        getRole() {
            return this.role
        }
    },

    actions: {
        setUser(user) {
            this.user = user
        },
        setAceessToken(token) {
            this.accessToken = token
        },
        setRefreshToken(token) {
            this.refreshToken = token
        },
        setRole(role) {
            this.role = role
        }
    }
})
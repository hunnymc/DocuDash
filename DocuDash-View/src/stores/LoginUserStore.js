import { defineStore } from 'pinia'
import axios from "axios";
import Cookies from "js-cookie";

export const useLoginUserStore = defineStore('loginUser', {
    state: () => ({
        userEmail: null,
        accessToken: null,
        refreshToken: null,
        role: null,
        previousPath: 0,
        adminViewUser: {}
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
        },
        getPreviousPath() {
            return this.previousPath
        },
        getAdminViewUser() {
            return this.adminViewUser
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
        },
        setPreviousPath(path) {
            this.previousPath = path
        },
        setAdminViewUser(user) {
            this.adminViewUser = user
        },
        async getUserInfo(userid) {
            let mainUrl = import.meta.env.VITE_API_URL;
            await axios.get(mainUrl + "/api/user/user-info/" + userid, {
                headers: { Authorization: `Bearer ${Cookies.get("accessToken")}` },
            })
                .then(response => {
                    this.adminViewUser = response.data
                })
                .catch(error => {
                    console.log(error.response.data.message)
                })
        }
    }
})
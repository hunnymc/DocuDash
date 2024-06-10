import {defineStore} from 'pinia';
import axios from 'axios';
import Cookies from "js-cookie";

export const useRefreshFunctionStore = defineStore('refreshFunctionStore', {
    state: () => ({
        isTokenAvailable: true, isLogin: false,
    }), persist: true, getters: {
        getIsTokenAvailable() {
            return this.isTokenAvailable;
        }, getIsLogin() {
            return this.isLogin;
        }
    }, actions: {
        setRefreshToken(token) {
            this.refreshToken = token;
        },
        setIsTokenAvailable(e) {
            this.isTokenAvailable = e;
        },
        setIsLogin(e) {
            this.isLogin = e;
        },
        async checkAccessToken(token) {

            let mainUrl = import.meta.env.VITE_API_URL;

            if (this.isLogin === true) {
                try {
                    // Make an API call to get the access token using the refresh token
                    await axios.get(mainUrl + "/api/user/check-token", {
                        headers: {Authorization: `Bearer ${Cookies.get("accessToken")}`},
                    });
                    return true;
                } catch (error) {
                    return false;
                }
            }

        },
    },

});



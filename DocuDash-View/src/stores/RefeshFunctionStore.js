import { defineStore } from 'pinia';
import axios from 'axios';

export const useRefreshFunctionStore = defineStore('refreshFunctionStore', {
    actions: {
        setRefreshToken(token) {
            this.refreshToken = token;
        },
        async getAccessToken() {
            // Make an API call to get the access token using the refresh token
            try {
                const response = await axios.post('https://api.example.com/token', {
                    refreshToken: this.refreshToken
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
    
                if (response.status === 200) {
                    this.accessToken = response.data.accessToken;
                } else {
                    console.error('Failed to get access token');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        },
    },
});
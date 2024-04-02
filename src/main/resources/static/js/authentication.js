"use strict";

function getAccessToken() {
    const authDataString = localStorage.getItem("auth");

    const authData = JSON.parse(authDataString);

    const accessToken = authData.access_token;

    if (accessToken) {
        return accessToken;
    } else {
        console.error('access token does not exist');
        return null;
    }
}
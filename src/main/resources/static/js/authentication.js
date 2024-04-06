function getAuthData() {
    const authData = JSON.parse(localStorage.getItem("auth"));

    if (!authData) {
        console.error('localStorage auth does not exist');
    }

    return authData || null;
}

function getAuthField(fieldName) {
    const authData = getAuthData();
    if (authData && authData[fieldName]) {
        return authData[fieldName];
    } else {
        console.error(`Auth ${fieldName} does not exist`);
        return null;
    }
}

function getAuthAccessToken() {
    return getAuthField('token');
}

function getAuthName() {
    return getAuthField('name');
}

function getAuthId() {
    return getAuthField('id');
}

function getAuthRole() {
    return getAuthField('role');
}

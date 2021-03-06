/**
 * Created by Vuletic on 26.5.2016.
 */
module.exports = [
    '$http', '$window', '$q', '$localStorage', 'jwtHelper',
    function loginService($http, $window, $q, $localStorage, jwtHelper){

        var service = {};

        service.login = login;
        service.logout = logout;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(username, password, callback) {

            /*$http.post('/api/korisnici/login', {username: username, password: password})
                .success(function (response) {
                    // ukoliko postoji token, prijava je uspecna
                    if (response.token) {
                        // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu
                        var currentUser = { username: username, token: response.token }
                        var tokenPayload = jwtHelper.decodeToken(response.token);
                        if(tokenPayload.role){
                            currentUser.role = tokenPayload.role;
                        }
                        // prijavljenog korisnika cuva u lokalnom skladistu
                        $localStorage.currentUser = currentUser;
                        // jwt token dodajemo u to auth header za sve $http zahteve
                        $http.defaults.headers.common.Authorization = response.token;
                        // callback za uspesan login
                        callback(true);
                    } else {
                        // callback za neuspesan login
                        callback(false);
                    }
                });*/

            $.post( '/api/korisnici/login', { username: username, password: password } )
                .done(function (response) {
                    // ukoliko postoji token, prijava je uspecna
                    if (response) {
                        // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu
                        var currentUser = { username: username, token: response }
                        var tokenPayload = jwtHelper.decodeToken(response);
                        if(tokenPayload.role){
                            currentUser.role = tokenPayload.role;
                        }
                        // prijavljenog korisnika cuva u lokalnom skladistu
                        $localStorage.currentUser = currentUser;
                        // jwt token dodajemo u to auth header za sve $http zahteve
                        $http.defaults.headers.common.Authorization = response;
                        console.log(response);
                        // callback za uspesan login
                        callback(true);
                    } else {
                        // callback za neuspesan login
                        callback(false);
                    }
                });

        }

        function logout() {
            // uklonimo korisnika iz lokalnog skladišta
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
            $window.location = "#/main";
        }

        function getCurrentUser() {
            return $localStorage.currentUser;
        }

    }
];
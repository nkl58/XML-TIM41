var myApp = angular.module('xmlApp',['ngRoute', 'ngStorage', 'ui.bootstrap','angular-jwt']);
myApp.controller('appCtrl', require('./appController.js'));
myApp.controller('mainCtrl', require('./mainController.js'));
myApp.controller('loginCtrl', require('./loginController.js'));
myApp.controller('usvojeniAktiCtrl', require('./usvojeniAktiController.js'));
myApp.controller('aktiUProceduriCtrl', require('./aktiUProceduriController.js'));
myApp.controller('kreirajAktCtrl', require('./kreirajAktController.js'));
myApp.controller('kreirajAmandmanCtrl', require('./kreirajAmandmanController.js'));
myApp.controller('rukovodjenjeSednicomCtrl', require('./rukovodjenjeSednicomController.js'));
myApp.controller('aktiUsvojeniUNaceluCtrl', require('./aktiUsvojeniUNaceluController.js'));
myApp.controller('amandmaniCtrl', require('./amandmaniController.js'));
myApp.controller('profilCtrl', require('./profilController.js'));
myApp.service('loginService', require('./loginService.js'));
myApp.controller('registerCtrl',require('./registerController.js'));

myApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/main', {
        templateUrl: '/angular/dist/templates/main.html',
        controller: 'mainCtrl'
      }).
	when('/usvojeni-akti', {
		templateUrl: '/angular/dist/templates/usvojeni-akti.html',
		controller: 'usvojeniAktiCtrl'
	}).
	when('/akti-u-proceduri', {
		templateUrl: '/angular/dist/templates/akti-u-proceduri.html',
		controller: 'aktiUProceduriCtrl'
	}).
	when('/kreiraj-akt', {
		templateUrl: '/angular/dist/templates/kreiraj-akt.html',
		controller: 'kreirajAktCtrl'
	}).
	when('/kreiraj-amandman/:id', {
		templateUrl: '/angular/dist/templates/kreiraj-amandman.html',
		controller: 'kreirajAmandmanCtrl'
	}).
	when('/rukovodjenje-sednicom', {
		templateUrl: '/angular/dist/templates/rukovodjenje-sednicom.html',
		controller: 'rukovodjenjeSednicomCtrl'
	}).
	when('/akti-usvojeni-u-nacelu', {
			templateUrl: '/angular/dist/templates/akti-usvojeni-u-nacelu.html',
			controller: 'aktiUsvojeniUNaceluCtrl'
	 }).
	 when('/amandmani/:id', {
		templateUrl: '/angular/dist/templates/amandmani.html',
		controller: 'amandmaniCtrl'
	 }).
	when('/profil', {
		templateUrl: '/angular/dist/templates/profil.html',
		controller: 'profilCtrl'
	}).
	when('/login', {
		templateUrl: '/angular/dist/templates/login.html',
		controller: 'loginCtrl'
	}).
      otherwise({
      	redirectTo: '/main'
      });

}]);



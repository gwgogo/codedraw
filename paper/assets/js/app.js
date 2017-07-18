var app = angular.module('dealList', ['ngCookies']).config(function ($locationProvider, $httpProvider, $cookiesProvider) {
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
	$httpProvider.defaults.withCredentials = true;
	 $cookiesProvider.defaults.path = '/';
  });


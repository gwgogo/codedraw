var app = angular.module('dealList', ['ngCookies']).config(function ($locationProvider) {
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false});
  });


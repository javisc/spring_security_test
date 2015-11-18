angular
		.module('hello', [ 'ngRoute' ])
		.config(
				function($routeProvider, $httpProvider) {

					$routeProvider.when('/', {
						templateUrl : 'home.html',
						controller : 'home'
					}).otherwise('/');

					$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

				}).controller('home', function($scope, $http) {
					    $http.get('resource/')
					      .success(function(data) {
					    	  $scope.greeting = data;
					    });
				}).controller('navigation', function($rootScope, $scope, $http, $location, $route) {

				  $http.get('user').success(function(data) {
				    if (data.name) {
				      $rootScope.authenticated = true;
				    } else {
				      $rootScope.authenticated = false;
				    }
				  }).error(function() {
				    $rootScope.authenticated = false;
				  });
				
				  $scope.credentials = {};
				
				  $scope.logout = function() {
				    $http.post('logout', {}).success(function() {
				      $rootScope.authenticated = false;
				      $location.path("/");
				    }).error(function(data) {
				      $rootScope.authenticated = false;
				    });
				  }

});
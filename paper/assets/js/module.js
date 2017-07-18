app.controller('printList', function($scope, $http) {
    $http.get('http://127.0.0.1:8080/products').
        then(function(response) {
            $scope.List = response.data;
        });
});
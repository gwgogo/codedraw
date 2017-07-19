app.controller('printList', function($scope, $http) {
    $http.get('http://52.231.28.248/apis/products').
        then(function(response) {
            $scope.List = response.data;
        });
});
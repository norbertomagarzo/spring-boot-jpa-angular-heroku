var Controllers = angular.module('Controllers', []);
Controllers.controller('ThingController', ['$scope', '$http', function ($scope, $http) {

$scope.create = function(){
        var name = angular.element('#new-thing-name').val();
        var number = angular.element('#new-thing-number').val();
        alert("Name: " + name | + "Number: " + number);
        var parameter = JSON.stringify({type: "Thing", name: name, number: number});
        $http.post("/thing/create", parameter)
                            .then(function (response) {
                                $scope.thing = null;
                                if (response.data == null) {
                                    $scope.thing = null;
                                }
                                $scope.thing = response.data;
                                $scope.name = response.data.name;
                                $scope.number = response.data.number;
                            });
        }

        $scope.list =
                $http.get('/things')
                                .then(function (response) {
                                  //  $scope.list = null;
                                    if (response.data == null) {
                                        $scope.list = null;
                                    }
                                    alert(response.data);
                                    $scope.list = response.data;
                                });

       $scope.updateList =  function(){
       $http.get('/things')
                               .then(function (response) {
                                 //  $scope.list = null;
                                   if (response.data == null) {
                                       $scope.list = null;
                                   }
                                   alert(response.data);
                                   $scope.list = response.data;
                               });
       }

    }]);

var Controllers = angular.module('Controllers', ['app.services']);
Controllers.controller('ThingController', ['$scope', '$http', '$rootScope','$global', function ($scope, $http, $rootScope, $global) {
    $scope.showForm = true;

    $scope.create = function(){
        var name = angular.element('#new-thing-name').val();
        var description = angular.element('#new-thing-description').val();
        if(!name){return}
        var parameter = JSON.stringify({type: "Thing", name: name, description: description});
        $http.post("/thing/create", parameter)
            .then(function(response){
            $scope.thing = null;
            if (response.data == null) {
                $scope.thing = null;
            }
            resetForm();
            $global.getList();
        });
        testList();
    }

    // test to be sure thing has been created and list updated
    function testList(){
        function check(l){
            var l = $rootScope.list;
            return l;
        }(function doit(){
            setTimeout(function(){
                console.log(
                    "list length: " + $rootScope.list.length + "\n"
                    + "last name added: " + $rootScope.list[$rootScope.list.length - 1].name)}, 3000);
        }());
    }

    function resetForm(){
        $scope.thingName = null;
        $scope.thingNumber = null;
        angular.element('#new-thing-name').val('');
        angular.element('#new-thing-description').val('');
        angular.element('#formButton').one('focus', function(e){$(this).blur();});
        angular.element('#formButton').blur();
    }



    $scope.updateThing = function(thingDetails){
        var name = angular.element('#edit-thing-name').val();
        var description = angular.element('#edit-thing-description').val();
        var id = $global.getDetails().id;
        if(!name){return}
        if(!id){return}
        var parameter = JSON.stringify({type: "Thing", id: id, name: name, description: description});
        $http.patch("/thing/update", parameter)
            .then(function(response){
            $scope.thing = null;
            if (response.data == null) {
                $scope.thing = null;
            }
            $global.setDetails(response.data);
        });
    }

}]);

Controllers.controller('ListController', ['$scope', '$http', '$rootScope','$global', function ($scope, $http, $rootScope, $global) {
    $rootScope.list = $global.getList();

    $scope.updateList =  function(){
        $scope.list = $global.getList();
    }

    $scope.viewDetails = function(item){
            var thingDetails = item;
            $global.clearDetails();
            $global.setDetails(thingDetails);
            $rootScope.thingDetails = item;
        }

    $scope.deleteAll = function(){
        $http.delete('/thing/delete/all')
            .then(function (response) {
            $rootScope.list = $global.getList();
            $global.setList(response.data);
            $global.clearDetails();
        });
    }

}]);

Controllers.controller('DetailsController', ['$scope', '$http', '$rootScope','$global', function ($scope, $http, $rootScope, $global) {
    $scope.hideDetails = function(){
        $global.clearDetails();
    }

    $scope.editThing = function(thingDetails){
        $rootScope.thingToEdit = thingDetails;
    }

    $scope.deleteThing = function(thingDetails){
        var id = thingDetails.id;
        $http.delete('/thing/'+id)
            .then(function (response) {
            $global.setList(response.data);
            $global.clearDetails();
            $global.getList();
        });
    }

}]);

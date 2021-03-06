module.exports = [
    '$scope', '$http', '$routeParams', '$route',
    function myController($scope, $http, $routeParams, $route){
        $http({
            method: "Post",
            url: "api/amandmani/akta",
            data: $routeParams.id,
            dataType: "json",
            traditional: true
        }).then(function (response) {
            $scope.data = response.data;

        });


        $http({
            method: "Get",
            url: "api/state/getState",
            dataType: "json",
            traditional:true

        }).then(function (response) {
            $scope.state = response.data.data;

        });


        $scope.glasoviZa;
        $scope.glasoviProtiv;
        $scope.glasoviUzdrzani;
        $scope.idAmandmana;
        $scope.naslov;

        var data = {};


        $scope.setParams = function(id, naslov){
            $scope.idAmandmana = id;
            $scope.naslov = naslov;

        }

        $scope.submitVotes = function(){

            data = { id: $scope.idAmandmana, glasoviZa: $scope.glasoviZa, glasoviProtiv: $scope.glasoviProtiv, glasoviUzdrzani: $scope.glasoviUzdrzani };

            $http({

                method: "Post",
                url: "api/vote/voteAmandman",
                data: data,
                dataType: "json",
                traditional:true
            }).then(function (response) {
                alert(response.data.data);
                angular.element(document.querySelector('#voteModal')).modal('hide');
                $route.reload();

            });

        }

    }

];
'use strict';

angular.module('try1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



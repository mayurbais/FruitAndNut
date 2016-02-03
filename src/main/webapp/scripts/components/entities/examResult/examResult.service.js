'use strict';

angular.module('try1App')
    .factory('ExamResult', function ($resource, DateUtils) {
        return $resource('api/examResults/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

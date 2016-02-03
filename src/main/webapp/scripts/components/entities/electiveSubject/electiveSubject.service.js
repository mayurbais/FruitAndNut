'use strict';

angular.module('try1App')
    .factory('ElectiveSubject', function ($resource, DateUtils) {
        return $resource('api/electiveSubjects/:id', {}, {
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

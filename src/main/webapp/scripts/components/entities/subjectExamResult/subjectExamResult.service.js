'use strict';

angular.module('try1App')
    .factory('SubjectExamResult', function ($resource, DateUtils) {
        return $resource('api/subjectExamResults/:id', {}, {
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

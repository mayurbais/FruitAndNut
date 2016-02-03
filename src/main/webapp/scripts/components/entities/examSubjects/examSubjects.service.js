'use strict';

angular.module('try1App')
    .factory('ExamSubjects', function ($resource, DateUtils) {
        return $resource('api/examSubjectss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                    data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    data.conductingDate = DateUtils.convertDateTimeFromServer(data.conductingDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

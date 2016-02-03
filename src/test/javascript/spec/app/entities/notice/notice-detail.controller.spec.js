'use strict';

describe('Controller Tests', function() {

    describe('Notice Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNotice, MockIrisUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNotice = jasmine.createSpy('MockNotice');
            MockIrisUser = jasmine.createSpy('MockIrisUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Notice': MockNotice,
                'IrisUser': MockIrisUser
            };
            createController = function() {
                $injector.get('$controller')("NoticeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:noticeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

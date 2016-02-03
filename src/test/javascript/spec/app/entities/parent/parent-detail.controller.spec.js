'use strict';

describe('Controller Tests', function() {

    describe('Parent Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockParent, MockIrisUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockParent = jasmine.createSpy('MockParent');
            MockIrisUser = jasmine.createSpy('MockIrisUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Parent': MockParent,
                'IrisUser': MockIrisUser
            };
            createController = function() {
                $injector.get('$controller')("ParentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:parentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

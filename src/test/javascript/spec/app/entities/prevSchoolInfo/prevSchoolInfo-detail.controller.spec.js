'use strict';

describe('Controller Tests', function() {

    describe('PrevSchoolInfo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrevSchoolInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrevSchoolInfo = jasmine.createSpy('MockPrevSchoolInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PrevSchoolInfo': MockPrevSchoolInfo
            };
            createController = function() {
                $injector.get('$controller')("PrevSchoolInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:prevSchoolInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

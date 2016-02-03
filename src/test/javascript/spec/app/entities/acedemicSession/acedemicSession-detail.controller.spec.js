'use strict';

describe('Controller Tests', function() {

    describe('AcedemicSession Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAcedemicSession, MockSchoolDetails;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAcedemicSession = jasmine.createSpy('MockAcedemicSession');
            MockSchoolDetails = jasmine.createSpy('MockSchoolDetails');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AcedemicSession': MockAcedemicSession,
                'SchoolDetails': MockSchoolDetails
            };
            createController = function() {
                $injector.get('$controller')("AcedemicSessionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:acedemicSessionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

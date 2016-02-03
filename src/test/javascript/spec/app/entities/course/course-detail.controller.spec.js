'use strict';

describe('Controller Tests', function() {

    describe('Course Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCourse, MockSubject, MockElectiveSubject, MockAcedemicSession;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCourse = jasmine.createSpy('MockCourse');
            MockSubject = jasmine.createSpy('MockSubject');
            MockElectiveSubject = jasmine.createSpy('MockElectiveSubject');
            MockAcedemicSession = jasmine.createSpy('MockAcedemicSession');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Course': MockCourse,
                'Subject': MockSubject,
                'ElectiveSubject': MockElectiveSubject,
                'AcedemicSession': MockAcedemicSession
            };
            createController = function() {
                $injector.get('$controller')("CourseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:courseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

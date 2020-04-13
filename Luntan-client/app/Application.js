/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('Luntan.Application', {
    extend: 'Ext.app.Application',

    name: 'Luntan',
//	requires:[],
    stores: ['CIDesignationStore', 'CourseGroupStore', 'DepartmentStore', 'UserRoleTypeStore',
    	'UserStore', 'FundingModelStore', 'CourseInstanceStore','EconomyDocStore','CourseStore', 'EDGStore', 'EDGKindStore',
    	'TeacherStore', 'ExaminerStore','EduBoardStore'
    ],

    launch: function () {
        // TODO - Launch the application

		Ext.Ajax.on('requestexception', function (connection, response, requestOptions, listenerOptions) {
			console.log("RequestException: " + response.status);
			if (response.status == 401) {
				window.open(Luntan.data.Constants.BASE_URL.concat('InREST.html'));
			} else if (response.status == 403) {
				Ext.MessageBox.alert('Status', 'Du saknar behörighet för detta');
			} else {
				Ext.MessageBox.alert('Status', 'RESTful-kommunikation gick inte som det skulle: ' + response.statusText + ' Skyll inte på mig om saker inte fungerar från och med nu!');
			}
		});


    	Ext.getStore('CIDesignationStore').load();
    	Ext.getStore('CourseGroupStore').load();
    	Ext.getStore('DepartmentStore').load();
    	Ext.getStore('EDGKindStore').load();
    	Ext.getStore('EduBoardStore').load();
    	Ext.getStore('UserRoleTypeStore').load();

    	Ext.getStore('UserStore').load();
    	Ext.getStore('FundingModelStore').load();
    	Ext.getStore('CourseStore').load();
    	Ext.getStore('CourseInstanceStore').load();
    	Ext.getStore('EDGStore').load();
    	Ext.getStore('EconomyDocStore').load();
    	Ext.getStore('TeacherStore').load();
    	Ext.getStore('ExaminerStore').load();
    },


    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'Den här applikationen har en uppdatering, ladda om?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});

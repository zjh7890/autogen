function getServices() {
    return JSON.stringify({
        rule: {
            ServiceName: "Rule",
            url_prefix: "/rule",
            TableName: "EagleDrlRule",
            withComment: true,
            methods: [
                {
                    type: "id",
                    sql: "id = $id",
                },
                {
                    type: "query",
                    sql: "id = $id and ruleName = $ruleName",
                    exclude: [
                        "ruleCode"
                    ],
                    methodLayers: {
                        mapper: {
                            methodHello: "hello"
                        }
                    }
                },
                {
                    type: "add",
                    exclude: [
                        "ruleCode"
                    ],
                },
                {
                    type: "update",
                    sql: "id = $id and ruleName = $ruleName",
                    exclude: [
                        "ruleCode"
                    ],
                },
                {
                    type: "upsert",
                    sql: "id = $id and ruleName = $ruleName",
                    exclude: [
                        "ruleCode"
                    ],
                },
                {
                    type: "delete",
                    sql: "id = $id and ruleName = $myRuleName",
                }
            ]
        },
        stu: {
            ServiceName: "Stu",
            url_prefix: "/stu",
            TableName: "Stu",
            withComment: true,
            methods: [
                {
                    type: "id",
                    sql: "id = $id",
                },
                {
                    type: "query",
                    sql: "stu_name = $stuName and age = $age and created_time > $created_time and updated_time > $updated_time",
                },
                {
                    type: "add",
                },
                {
                    type: "update",
                    sql: "id = $id",
                },
                {
                    type: "upsert",
                    sql: "id = $id",
                },
                {
                    type: "delete",
                    sql: "id = $id",
                }
            ]
        },
        recall_2023_user: {
            ServiceName: "Recall2023User",
            url_prefix: "/Recall2023User",
            TableName: "Recall2023User",
            withComment: true,
            methods: [
                {
                    type: "id",
                    sql: "id = $id",
                },
                {
                    type: "query",
                    sql: "uid = $uid",
                },
                {
                    type: "add",
                },
                {
                    type: "update",
                    sql: "id = $id",
                },
                {
                    type: "upsert",
                    sql: "id = $id",
                },
                {
                    type: "delete",
                    sql: "id = $id",
                }
            ]
        }
    })
}

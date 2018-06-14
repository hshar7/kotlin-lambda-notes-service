package com.hshar.lambda1

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.log4j.BasicConfigurator
import org.slf4j.LoggerFactory
import spark.Spark.*

class LambdaHandler @Throws(ContainerInitializationException::class)
constructor() : RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private val handler = SparkLambdaContainerHandler.getAwsProxyHandler()
    private var initialized = false

    override fun handleRequest(awsProxyRequest: AwsProxyRequest, context: Context?): AwsProxyResponse {
        if(!initialized){
            defineRoutes()
            initialized = true
        }
        return handler.proxy(awsProxyRequest, context)
    }

    companion object {
        private val log = LoggerFactory.getLogger(LambdaHandler::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            defineRoutes()
        }

        private fun defineRoutes(){
            BasicConfigurator.configure()
            initExceptionHandler { e ->
                log.error("Spark init failure", e)
                System.exit(100)
            }
            get("/note/:id"){req, res -> ApiActions().getNote(req, res)}
            post("/note"){req, res -> ApiActions().insertNote(req, res)}
            get("/notes"){req, res -> ApiActions().getAllNoes(req, res)}
            delete("/note/:id"){req, res -> ApiActions().deleteNote(req, res)}
            put("/note/:id"){req, res -> ApiActions().updateNote(req, res)}
        }
    }
}

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pdf.label', default: 'Pdf')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>
<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'pdf.id.label', default: 'Id')}"/>

                <g:sortableColumn property="path" title="${message(code: 'pdf.path.label', default: 'Path')}"/>

                <g:sortableColumn property="fileName" title="${message(code: 'pdf.fileName.label', default: 'File Name')}"/>

                <g:sortableColumn property="mimeType" title="${message(code: 'pdf.mimeType.label', default: 'Mime Type')}"/>
                <th>View</th>
                <th>View with Menu</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${pdfInstanceList}" status="i" var="pdfInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show" id="${pdfInstance.id}">${fieldValue(bean: pdfInstance, field: "id")}</g:link></td>

                    <td><g:link action="download" id="${pdfInstance.id}">${fieldValue(bean: pdfInstance, field: "path")}</g:link></td>

                    <td>${fieldValue(bean: pdfInstance, field: "fileName")}</td>

                    <td>${fieldValue(bean: pdfInstance, field: "mimeType")}</td>
                    <g:set var="fullPath" value="${(pdfInstance.path).toString()+'/'+ pdfInstance.fileName.toString()}"/>
                    <td>
                        <pdf:isSupportedByGoogleDocs params="['fullPath':fullPath]">
                            View on Google Docs
                        </pdf:isSupportedByGoogleDocs>
                    </td>
                    <td>
                        <pdf:isSupportedByGoogleDocs params="['fullPath':fullPath,chrome:true]">
                           Chrome Mode
                        </pdf:isSupportedByGoogleDocs>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${pdfInstanceTotal}"/>
    </div>
</div>
</body>
</html>

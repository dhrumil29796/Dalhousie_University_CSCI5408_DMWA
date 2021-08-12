package com.dal.database.utils;

import com.dal.database.CreateQueries.*;
import com.dal.database.DataStorage.Database;
import com.dal.database.DataStorage.Table;
import com.dal.database.DataStorage.TableRowEntryStructure;
import com.dal.database.Login.AttemptLogin;
import com.dal.database.Login.RegisterUser;
import com.dal.database.PrintInfo;
import com.dal.database.queryManagement.SplitQuery;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class InputFromUser {

  private static InputFromUser instance = null;
  final PrintInfo printer = PrintInfo.getInstance();
  final RegisterUser registerUser = new RegisterUser();

  private InputFromUser() {
    try {
      new BasicFolderStructure();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public static InputFromUser getInstance() {
    if (instance == null) {
      instance = new InputFromUser();
    }
    return instance;
  }

  public void inputsFromUser(Scanner sc) {

    AttemptLogin attemptLogin = new AttemptLogin();
    if (!attemptLogin.loginUser(new Scanner(System.in))) {
      System.exit(0);
    }
    for (; ; ) {

      System.out.print(BasicInformation.getInstance().getLoginUser() + "@DVM.sql >>>>> ");
      String input = sc.nextLine();
      LogGenerator.getInstance().writeToQueryLogFile(input + "\n");
      LogGenerator.getInstance().writeToGeneralLogFile(input + "\n");
      LogGenerator.getInstance().writeToEventLogFile(input + "\n");
      SplitQuery splitQuery = new SplitQuery(input);
      List<String> inputTokens = splitQuery.splitQueryTokens();
      if (!evaluateInput(inputTokens)) {
        continue;
      }

    }
  }

  public void registerUser(Scanner scanner) {

    printer.printMessage("\n\tSet username : ");
    final String userName = scanner.nextLine();
    if (!Validation.isValidInput(userName)) {
      printer.printMessage("Username is not valid.\n");
      return;
    }

    printer.printMessage("\n\tSet password (alphanumeric) : ");
    final String password = scanner.nextLine();
    if (!Validation.isAlphaNumeric(password)) {
      printer.printMessage("\n\tPassword is not valid.\n");
      return;
    }

    printer.printMessage("\n\tWho is your favourite hero? ");
    final String securityQ1 = scanner.nextLine();
    if (!Validation.isValidInput(securityQ1)) {
      printer.printMessage("Entered answer is not valid.\n");
      return;
    }

    printer.printMessage("\n\tWhat is the name of your pet? ");
    final String securityQ2 = scanner.nextLine();
    if (!Validation.isValidInput(securityQ2)) {
      printer.printMessage("Entered answer is not valid.\n");
      return;
    }

    printer.printMessage("\n\tWho is your favourite superhero? ");
    final String securityQ3 = scanner.nextLine();
    if (!Validation.isValidInput(securityQ3)) {
      printer.printMessage("Entered answer is not valid.\n");
      return;
    }

    final String hashedPassword = HashAlgorithm.getSHA256Hash(password);

    if (Validation.userExists(userName, password)) {
      printer.printMessage("\n\tUser already exists");
    } else {
      if (registerUser.registerNewUser(userName, hashedPassword) && registerUser.writeUsersToFile()) {
        printer.printMessage("\n\tUser registered successfully!");
      }
    }
  }


  private boolean evaluateInput(List<String> tokens) {
    if (tokens == null || tokens.size() <= 0) {
      return false;
    }

    List<String> newTokens = getSubTokens(tokens);

    switch (tokens.get(0).toLowerCase()) {
      case "exit": {
        return (exitQuery(newTokens));
      }
      case "quit": {
        return (exitQuery(newTokens));
      }
      case "commit": {
        return (commit(newTokens));

      }
      case "create": {
        return (create(newTokens));
      }
      case "use": {
        return (useDatabase(newTokens));
      }
      case "show": {
        return showDatabases(newTokens);
      }
      case "insert": {
        return InsertIntoTable(newTokens);
      }
      case "rollback": {
        return RollbackValues(newTokens);
      }
      case "select": {
        return selectQuery(newTokens);
      }
      case "delete": {
        return deleteFromTable(newTokens);
      }
      case "update": {
        return updateFromTable(newTokens);
      }
      case "desc": {
        return describeTable(newTokens);
      }
      case "describe": {
        return describeTable(newTokens);
      }
      case "all": {
        return showTables(newTokens);
      }
      case "erd": {
        return erdDiagram(newTokens);
      }
      case "sqldump": {
        return sqlDump(newTokens);
      }
      case "drop": {
        return dropDatabase(newTokens);
      }

      default: {
        PrintInfo.getInstance().commandError();
        return false;
      }
    }

  }

  private boolean dropDatabase(List<String> tokens) {

    if (endOfQuery(tokens) || !tokens.get(0).equalsIgnoreCase("database")) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String databaseName = tokens.get(0).toUpperCase();
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      DropDatabase dropDatabase = new DropDatabase();
      return dropDatabase.dropThisDatabase(databaseName);
    }

    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean sqlDump(List<String> tokens) {

    if (endOfQuery(tokens) || !tableQueryBasicCheck()) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String key = tokens.get(0).toLowerCase();
    tokens = getSubTokens(tokens);
    if (!endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    SQLDump sqlDumpData = new SQLDump();
    if (key.equalsIgnoreCase("save")) {
      sqlDumpData.writeSQLDump();
      return true;
    } else if (key.equalsIgnoreCase("get")) {
      sqlDumpData.readSQL();
      return true;
    }
    return false;
  }

  private boolean showTables(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens) || !"tables".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);

    if (endOfQuery(tokens)) {
      ShowTables showtables = new ShowTables();
      return true;
    }
    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean describeTable(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String tableName = tokens.get(0).toUpperCase();
    Table table = BasicInformation.getInstance().fetchDatabase().tables.get(tableName);
    tokens = getSubTokens(tokens);
    if (table == null) {
      PrintInfo.getInstance().printError("\n\tTable does not exist!!!!\n");
      return false;
    }
    if (endOfQuery(tokens)) {
      DescribeTable desc = new DescribeTable();
      return desc.descThisTable(table);
    }
    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean updateFromTable(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String tableName = tokens.get(0).toUpperCase();
    Table table = BasicInformation.getInstance().fetchDatabase().tables.get(tableName);
    tokens = getSubTokens(tokens);
    if (table == null) {
      PrintInfo.getInstance().printError("\n\tTable does not exist!!!!\n");
      return false;
    }
    if (endOfQuery(tokens) || !"set".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String columnName = tokens.get(0).toUpperCase();
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    if ("'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
      if (endOfQuery(tokens)) {
        PrintInfo.getInstance().commandError();
        return false;
      }
    }
    String value = tokens.get(0);
    tokens = getSubTokens(tokens);
    if ("'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
    }
    UpdateTable update = new UpdateTable();
    if (endOfQuery(tokens)) {
      return update.updateThisTable(table, table, columnName, value);
    }
    if (!"where".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    Table updateTable = whereConditionEvaluation(tokens, table);
    return update.updateThisTable(table, updateTable, columnName, value);

  }

  private boolean deleteFromTable(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens) || !"FROM".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String tableName = tokens.get(0).toUpperCase();
    Table table = BasicInformation.getInstance().fetchDatabase().tables.get(tableName);
    tokens = getSubTokens(tokens);
    if (table == null) {
      PrintInfo.getInstance().printError("\n\tTable does not exist!!!!\n");
      return false;
    }
    DeleteTableEntry delete = new DeleteTableEntry();
    if (endOfQuery(tokens)) {
      return delete.deleteTableEntries(table);

    }
    if (!"where".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    Table removeTable = whereConditionEvaluation(tokens, table);
    return delete.deleteTableEntries(table, removeTable);

  }

  private boolean selectQuery(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens) || !("*".equalsIgnoreCase(tokens.get(0)) || "(".equalsIgnoreCase(tokens.get(0)))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String val = tokens.get(0);
    tokens = getSubTokens(tokens);
    List<String> columnNames = null;
    Map<String, String> columnMap = null;
    if ("(".equals(val)) {
      columnNames = getBracketTokens(tokens, true);
      if (columnNames == null) {
        return false;
      }
      int index = getIndexOfClosingBracket(tokens);
      if (index < 0) {
        PrintInfo.getInstance().commandError();
        return false;
      }
      tokens = tokens.subList(index, tokens.size());
      tokens = getSubTokens(tokens);
    } else if (!"*".equals(val)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    if (!"From".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);

    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    String tableName = tokens.get(0);
    tokens = getSubTokens(tokens);

    if (!regexValidationOfName(tableName)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tableName = tableName.toUpperCase();
    Database database = BasicInformation.getInstance().fetchDatabase();
    if (database == null || database.tables == null || !database.tables.containsKey(tableName)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    if (columnNames != null) {
      columnMap = columnListToMap(columnNames, database.tables.get(tableName).columnNamesAndInputType);
    }

    if (endOfQuery(tokens)) {
      Select select = new Select();
      if (columnNames == null) {
        select.printThisTable(database.tables.get(tableName));
      } else {
        select.printThisTable(database.tables.get(tableName), columnMap);
      }
      return true;
    }

    if (!"Where".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);

    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    Table table = whereConditionEvaluation(tokens, database.tables.get(tableName));
    if (table == null) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    Select select = new Select();
    if (columnNames == null) {
      select.printThisTable(table);
    } else {
      select.printThisTable(table, columnMap);
    }

    return true;
  }

  private Table whereConditionEvaluation(List<String> tokens, Table table) {
    Table newTable = table.duplicateTable();
    List<TableRowEntryStructure> rows = conditionRecursion(table, tokens);
    if (rows == null) {
      return null;
    }

    newTable.rows = rows;
    return newTable;
  }

  private List<TableRowEntryStructure> conditionRecursion(Table table, List<String> tokens) {
    List<TableRowEntryStructure> firstList = conditionEvaluation(table, tokens);
    if (firstList == null) {
      return null;
    }

    tokens = getSubTokens(getSubTokens(tokens));
    if ("'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
    }
    tokens = getSubTokens(tokens);
    if (!endOfQuery(tokens) && "'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
    }

    if (endOfQuery(tokens)) {
      return firstList;
    }

    String operator = tokens.get(0);
    tokens = getSubTokens(tokens);
    List<TableRowEntryStructure> secondList = conditionRecursion(table, tokens);
    if (secondList == null) {
      return null;
    }
    switch (operator.toUpperCase()) {
      case "AND": {
        return intersection(firstList, secondList);
      }
      case "OR": {
        return union(firstList, secondList);
      }
      default: {
        PrintInfo.getInstance().printError("\n\tOperator: '" + operator + "' not supported!!!\n");
        return null;
      }
    }
  }

  private List<TableRowEntryStructure> intersection(List<TableRowEntryStructure> rows1, List<TableRowEntryStructure> rows2) {
    Set<TableRowEntryStructure> set = new HashSet<>();
    List<TableRowEntryStructure> newRows = new ArrayList<>();
    set.addAll(rows2);
    for (TableRowEntryStructure row : rows1) {
      if (set.contains(row)) {
        newRows.add(row);
      }
    }
    return newRows;
  }

  private List<TableRowEntryStructure> union(List<TableRowEntryStructure> rows1, List<TableRowEntryStructure> rows2) {
    Set<TableRowEntryStructure> set = new HashSet<>();
    set.addAll(rows2);
    set.addAll(rows1);

    return new ArrayList<>(set);
  }

  private List<TableRowEntryStructure> conditionEvaluation(Table table, List<String> tokens) {
    List<TableRowEntryStructure> newList = new ArrayList<>();
    if (endOfQuery(tokens) || table == null || table.rows == null || table.rows.size() <= 0) {
      PrintInfo.getInstance().commandError();
      return null;
    }
    String columnName = tokens.get(0).toUpperCase();
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return null;
    }
    String operator = tokens.get(0);
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return null;
    }
    if ("'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
    }

    String value = tokens.get(0);
    tokens = getSubTokens(tokens);

    if (!endOfQuery(tokens) && "'".equals(tokens.get(0))) {
      tokens = getSubTokens(tokens);
    }

    for (TableRowEntryStructure row : table.rows) {
      if (!row.Inputs.containsKey(columnName)) {
        PrintInfo.getInstance().printError("\n\tThe column name: " + columnName + " Does not exit in table!!!\n");
        return null;
      }
      Object object = row.Inputs.get(columnName);
      switch (operator) {
        case "=": {
          if (object != null && value.equals(object.toString())) {
            newList.add(row);
          }
          break;
        }
        case "!=": {
          if (object == null || !value.equals(object.toString())) {
            newList.add(row);
          }
          break;
        }
        case "<": {
          if (object != null && value.compareToIgnoreCase(object.toString()) > 0) {
            newList.add(row);
          }
          break;
        }
        case ">": {
          if (object != null && value.compareToIgnoreCase(object.toString()) < 0) {
            newList.add(row);
          }
          break;
        }
        case "<=": {
          if (object != null && value.compareToIgnoreCase(object.toString()) >= 0) {
            newList.add(row);
          }
          break;
        }
        case ">=": {
          if (object != null && value.compareToIgnoreCase(object.toString()) <= 0) {
            newList.add(row);
          }
          break;
        }
        default: {
          PrintInfo.getInstance().printError("\n\tEnter Correct where condition operator!!!!\n");
        }

      }
    }
    return newList;
  }

  private Map<String, String> columnListToMap(List<String> columns, Map<String, String> columnNamesWithType) {
    Map<String, String> newColumnsMap = new LinkedHashMap<>();
    for (String col : columns) {
      col = col.toUpperCase();
      if (!columnNamesWithType.containsKey(col)) {
        PrintInfo.getInstance().printError("Column: '" + col + "' Does not exist!!!!");
        return null;
      }
      newColumnsMap.put(col, columnNamesWithType.get(col));
    }
    return newColumnsMap;
  }

  private boolean InsertIntoTable(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }

    if (!tokenListValidation(tokens) || !"into".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String tableName = tokens.get(0);
    if (!BasicInformation.getInstance().fetchDatabase().tables.containsKey(tableName.toUpperCase())) {
      PrintInfo.getInstance().printError("\n\tTable does not exist!!!\n");
      return false;
    }

    tokens = getSubTokens(tokens);
    if (!tokenListValidation(tokens) || !"(".equals(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    List<String> columnNames = getBracketTokens(tokens, true);
    if (columnNames == null) {
      return false;
    }
    int index = getIndexOfClosingBracket(tokens);
    if (index < 0) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = tokens.subList(index, tokens.size());
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    if (!"values".equalsIgnoreCase(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    tokens = getSubTokens(tokens);
    if (endOfQuery(tokens) || !"(".equals(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    tokens = getSubTokens(tokens);

    List<String> columnValues = getBracketTokens(tokens, false);
    if (columnValues == null) {
      return false;
    }

    if (columnNames.size() != columnValues.size()) {
      PrintInfo.getInstance().commandError();
      return false;
    }

    InsertIntoTable insert = new InsertIntoTable();
    Map<String, Object> row = fetchMapForRow(tableName, columnNames, columnValues);
    if (row == null) {
      return false;
    }

    return insert.InsertIntoTableValues(tableName, row);

  }

  private boolean RollbackValues(List<String> tokens) {
    if (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0))) {
      PrintInfo.getInstance().printMessage("\n\tRollback Successful!\n\t");
      new Rollback();
      return true;
    }

    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean useDatabase(List<String> tokens) {
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String databaseName = tokens.get(0);
    tokens = getSubTokens(tokens);

    if (!(tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0)))) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    UseDatabase useDatabase = new UseDatabase();
    return useDatabase.UseThisDatabase(databaseName);

  }

  private boolean showDatabases(List<String> tokens) {
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    String query = tokens.get(0);
    tokens = getSubTokens(tokens);

    if (!endOfQuery(tokens) || !"databases".equalsIgnoreCase(query)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    ShowDatabases showDatabases = new ShowDatabases();
    showDatabases.showAllDatabases();

    return true;

  }

  private boolean exitQuery(List<String> tokens) {
    if (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0))) {
      PrintInfo.getInstance().printMessage("\n\tExit\n\t");
      System.exit(0);
    }

    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean commit(List<String> tokens) {
    if (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0))) {
      PrintInfo.getInstance().printMessage("\n\tCommit Successful!\n\t");
      Commit commit = new Commit();

      return true;
    }

    PrintInfo.getInstance().commandError();
    return false;

  }

  private boolean erdDiagram(List<String> tokens) {
    if (!endOfQuery(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    ERDDiagram erd = new ERDDiagram();
    erd.erdDiagram();
    return true;
  }

  private boolean create(List<String> tokens) {
    if (tokenListValidation(tokens)) {
      switch (tokens.get(0).toLowerCase()) {
        case "database": {
          return (createDatabase(getSubTokens(tokens)));
        }
        case "table": {
          return (createTable(getSubTokens(tokens)));
        }
        default: {
          PrintInfo.getInstance().commandError();
          return false;
        }
      }
    } else {
      PrintInfo.getInstance().commandError();
    }
    return false;
  }

  private boolean createDatabase(List<String> tokens) {
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    if (!regexValidationOfName(tokens.get(0))) {
      PrintInfo.getInstance().printError("\n\tDatabase Name should only contain characters\n");
      return false;
    }
    String name = tokens.get(0);
    tokens = getSubTokens(tokens);
    if (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0))) {
      CreateDatabase createDatabase = new CreateDatabase();
      return (createDatabase.addDatabase(name));
    }
    PrintInfo.getInstance().commandError();
    return false;
  }

  private boolean createTable(List<String> tokens) {
    if (!tableQueryBasicCheck()) {
      return false;
    }
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return false;
    }
    if (!regexValidationOfName(tokens.get(0))) {
      PrintInfo.getInstance().printError("\n\tTable Name should only contain characters\n");
      return false;
    }
    String name = tokens.get(0).toUpperCase();
    tokens = getSubTokens(tokens);
    if (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0))) {
      PrintInfo.getInstance().printError("\n\tEnter input columns for the tables\n");
      return false;
    }
    List<String> columns = fetchColumnsForTableCreate(tokens);
    if (columns == null) {
      return false;
    }
    Map<String, String> columnAndType = fetchColumnAndTypeMap(columns);
    if (columnAndType == null) {
      return false;
    }

    CreateTable createTable = new CreateTable();
    return createTable.addTable(name, columnAndType);
  }

  private List<String> fetchColumnsForTableCreate(List<String> tokens) {
    if (!("(").equals(tokens.get(0))) {
      PrintInfo.getInstance().commandError();
      return null;
    }
    tokens = getSubTokens(tokens);
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return null;
    }
    int lastIndex = tokens.size() - 1;
    String last = tokens.get(lastIndex);
    String secondLast = tokens.get(lastIndex - 1);
    if (")".equals(last)) {
      return tokens.subList(0, lastIndex);
    } else if (";".equals(last) && ")".equals(secondLast)) {
      return tokens.subList(0, lastIndex - 1);
    }

    PrintInfo.getInstance().commandError();
    return null;
  }

  private Map<String, String> fetchColumnAndTypeMap(List<String> tokens) {
    Map<String, String> columnsWithDataType = new LinkedHashMap<>();
    for (; ; ) {
      if (tokens == null || tokens.size() <= 0) {
        break;
      }
      if (tokens.size() < 2) {
        PrintInfo.getInstance().commandError();
        return null;
      }
      String columnName = tokens.get(0).toUpperCase();
      String columnType = tokens.get(1);
      if (!regexValidationOfName(columnName) || !regexValidationOfName(columnType) || columnDataTypeValidation(columnType, columnsWithDataType) == null) {
        PrintInfo.getInstance().commandError();
        return null;
      }

      columnType = columnDataTypeValidation(columnType, columnsWithDataType);

      columnsWithDataType.put(columnName, columnType);
      tokens = getSubTokens(getSubTokens(tokens));
      if (tokens != null && tokens.size() > 0) {
        if ((",").equals(tokens.get(0))) {
          tokens = getSubTokens(tokens);
        } else {
          PrintInfo.getInstance().commandError();
          return null;
        }
      }
    }
    return columnsWithDataType;
  }

  private boolean regexValidationOfName(String name) {
    String regex = "^[a-zA-Z]++$";

    Pattern VALIDATE = Pattern.compile(regex,
        Pattern.CASE_INSENSITIVE);
    if (!VALIDATE.matcher(name).matches()) {
      return false;
    }
    return true;
  }

  private List<String> getSubTokens(List<String> tokens) {
    List<String> newTokens = null;
    if (tokens.size() > 1) {
      newTokens = tokens.subList(1, tokens.size());
    }
    return newTokens;
  }

  private boolean tokenListValidation(List<String> tokens) {
    return (tokens != null && tokens.size() >= 1);
  }

  private String columnDataTypeValidation(String datatype, Map<String, String> columnNameAndDataType) {
    switch (datatype.toLowerCase()) {
      case "string": {
        return "String";
      }
      case "double": {
        return "Double";
      }
      case "boolean": {
        return "Boolean";
      }
      case "int": {
        return "Integer";
      }
      case "integer": {
        return "Integer";
      }
      default: {
        if (columnNameAndDataType.containsKey(datatype.toUpperCase())) {
          return datatype.toUpperCase();
        }
        return null;
      }
    }

  }

  private boolean tableQueryBasicCheck() {
    if (BasicInformation.getInstance().fetchDatabase() == null) {
      PrintInfo.getInstance().printError("\n\tSelect Database First!!!\n");
      return false;
    }
    return true;
  }

  private boolean endOfQuery(List<String> tokens) {
    return (tokens == null || tokens.size() <= 0 || (";").equals(tokens.get(0)));
  }

  private int getIndexOfClosingBracket(List<String> tokens) {
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return -1;
    }
    int t = 0;
    for (; ; ) {
      if (endOfQuery(tokens)) {
        return -1;
      }
      if ((")").equals(tokens.get(t))) {
        break;
      }
      t++;
    }
    return t;
  }

  private Map<String, Object> fetchMapForRow(String tableName, List<String> columnNames, List<String> columnValues) {
    tableName = tableName.toUpperCase();
    Map<String, Object> row = new LinkedHashMap<>();
    Map<String, String> columnAndDatatype = BasicInformation.getInstance().fetchDatabase().tables.get(tableName).columnNamesAndInputType;
    for (int t = 0; t < columnNames.size(); t++) {
      if (!columnAndDatatype.containsKey(columnNames.get(t).toUpperCase())) {
        PrintInfo.getInstance().printError("\n\tRow does not exist\n");
        return null;
      }
      switch (columnAndDatatype.get(columnNames.get(t).toUpperCase()).toLowerCase()) {
        case "integer": {
          try {
            Integer.parseInt(columnValues.get(t));
            row.put(columnNames.get(t).toUpperCase(), columnValues.get(t));
            break;
          } catch (Exception e) {
            PrintInfo.getInstance().printError("\n\tData type of input parameter: \" " + columnNames.get(t) + " \" is wrong\n");
            return null;
          }
        }
        case "double": {
          try {
            Double.parseDouble(columnValues.get(t));
            row.put(columnNames.get(t).toUpperCase(), columnValues.get(t));
            break;
          } catch (Exception e) {
            PrintInfo.getInstance().printError("\n\tData type of input parameter: \" " + columnNames.get(t) + " \" is wrong\n");
            return null;
          }
        }
        case "boolean": {
          try {
            Boolean.parseBoolean(columnValues.get(t));
            row.put(columnNames.get(t).toUpperCase(), columnValues.get(t));
            break;
          } catch (Exception e) {
            PrintInfo.getInstance().printError("\n\tData type of input parameter: \" " + columnNames.get(t) + " \" is wrong\n");
            return null;
          }
        }
        case "string": {
          try {
            (columnValues.get(t)).toString();
            row.put(columnNames.get(t).toUpperCase(), columnValues.get(t));
            break;
          } catch (Exception e) {
            PrintInfo.getInstance().printError("\n\tData type of input parameter: \" " + columnNames.get(t) + " \" is wrong\n");
            return null;
          }
        }
        default: {
          PrintInfo.getInstance().printError("\n\tData type of input parameter: \" " + columnNames.get(t) + " \" is wrong\n");
          return null;
        }
      }

    }
    return row;
  }

  private List<String> getBracketTokens(List<String> tokens, boolean RegexRequired) {
    if (!tokenListValidation(tokens)) {
      PrintInfo.getInstance().commandError();
      return null;
    }

    List<String> newTokens = new ArrayList<>();

    for (; ; ) {
      if (!tokenListValidation(tokens)) {
        PrintInfo.getInstance().commandError();
        return null;
      }
      if ("'".equals(tokens.get(0))) {
        tokens = getSubTokens(tokens);
      }
      if (endOfQuery(tokens) || (!regexValidationOfName(tokens.get(0)) && RegexRequired)) {
        PrintInfo.getInstance().commandError();
        return null;
      }
      newTokens.add(tokens.get(0));
      tokens = getSubTokens(tokens);

      if (endOfQuery(tokens)) {
        PrintInfo.getInstance().commandError();
        return null;
      }

      if ("'".equals(tokens.get(0))) {
        tokens = getSubTokens(tokens);
      }
      if (")".equals(tokens.get(0))) {
        break;
      }
      if (!",".equals(tokens.get(0))) {
        PrintInfo.getInstance().commandError();
        return null;
      }
      if (tokens == null) {
        PrintInfo.getInstance().commandError();
        return null;
      }
      tokens = getSubTokens(tokens);
    }
    return newTokens;
  }


}

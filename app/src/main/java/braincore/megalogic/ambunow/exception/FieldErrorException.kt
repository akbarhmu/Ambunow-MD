package braincore.megalogic.ambunow.exception

class FieldErrorException(val errorFields: List<Pair<Int, Int>>) : Exception()
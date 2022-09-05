package com.dag.mvvmarchitectureexample.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.dag.mvvmarchitectureexample.data.Onboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object OnboardSerializer : Serializer<Onboard> {
    override val defaultValue: Onboard
        get() = Onboard.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Onboard = withContext(Dispatchers.IO){
        try {
            return@withContext Onboard.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Onboard, output: OutputStream) = withContext(Dispatchers.IO){ t.writeTo(output) }
}
